package ai.sken.jenkins.plugins;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import jenkins.tasks.SimpleBuildStep;

public class SkenCLIBuilder extends Builder implements SimpleBuildStep {

	private final String orgId;
	private final String appId;

    @DataBoundConstructor
    public SkenCLIBuilder(String orgId, String appId) {
        this.orgId = orgId;
        this.appId = appId;
    }

	public String getOrgId() {
		return orgId;
	}

	public String getAppId() {
		return appId;
	}

	@Override
	public void perform(Run<?, ?> run, FilePath workDir, Launcher launcher, TaskListener listener)
			throws InterruptedException, IOException {
	
		Map map = new HashMap();
		execute(workDir, listener, map, "pip", "uninstall", "--yes", "skencli");
		execute(workDir, listener, map, "pip", "install", "--user", "--upgrade", "skencli");

		execute(workDir, listener, map, "skencli", "--version");
		execute(workDir, listener, map, "skencli", "--org_id", orgId, "--app_id", appId);

		execute(workDir, listener, map, "/var/lib/jenkins/.local/bin/skencli", "--version");
		execute(workDir, listener, map, "/var/lib/jenkins/.local/bin/skencli", "--org_id", orgId, "--app_id", appId);
		
	}

    @Symbol("skenai")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckOrgId(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.SkenCLIBuilder_DescriptorImpl_errors_missingId());
            if (value.length() < 36)
                return FormValidation.warning(Messages.SkenCLIBuilder_DescriptorImpl_warnings_tooShort());
            return FormValidation.ok();
        }

        public FormValidation doCheckAppId(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.SkenCLIBuilder_DescriptorImpl_errors_missingId());
            if (value.length() < 36)
                return FormValidation.warning(Messages.SkenCLIBuilder_DescriptorImpl_warnings_tooShort());
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return Messages.SkenCLIBuilder_DescriptorImpl_DisplayName();
        }

    }

	/**
	 * 
	 * @param map
	 *            store env variable<K,V>
	 * @param script
	 *            arbitrary arguments, the first is script, the next is
	 *            arguments
	 * @return 0 represent normal
	 */
	public static int execute(FilePath workDir, TaskListener listener, Map<String, String> map, String... arguments) {
		String workDirPath = workDir.getRemote().toString();
		listener.getLogger().print("Working directory: " + workDirPath + "\n");
		listener.getLogger().print("Executing skencli command: ");
		for (String command : arguments) {
			listener.getLogger().print(command);
			listener.getLogger().print(" ");
		}
		listener.getLogger().println();

		ProcessBuilder pb = new ProcessBuilder(arguments);
		pb.directory(new File(workDirPath));
		Map<String, String> env = pb.environment();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			env.put(entry.getKey(), entry.getValue());
		}
		Process p = null;
		int exitValue = 0;
		try {
			p = pb.start();

			if (p != null) {
				p.waitFor();
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.defaultCharset()));
			BufferedReader brErr = new BufferedReader(new InputStreamReader(p.getErrorStream(), Charset.defaultCharset()));

			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				listener.getLogger().println(line);
			}
			while ((line = brErr.readLine()) != null) {
				System.out.println(line);
				listener.getLogger().println(line);
			}

			br.close();
			brErr.close();

			System.out.println(p.exitValue());
			exitValue  = p.exitValue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (p != null) {
				p.destroy();
			}
		}
		return exitValue;
	}
    
}
