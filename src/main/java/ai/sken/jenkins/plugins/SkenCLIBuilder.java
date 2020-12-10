package ai.sken.jenkins.plugins;

import java.io.BufferedReader;
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
	public void perform(Run<?, ?> run, FilePath arg1, Launcher arg2, TaskListener listener)
			throws InterruptedException, IOException {
		run.addAction(new SkenCLIAction());
		
		Map map = new HashMap();
		// map.put("JAVA_HOME", "/Library/Java/JavaVirtualMachines/jdk1.7.0_51.jdk/Contents/Home");
		// map.put("NODE_PATH", "/usr/local/lib/node_modules/");
		// String script = "/Users/david/Documents/workspace/Cable/src/main/java/com/ericsson/iptv/testcase/test.sh";
		String script = "skencli";
		execute(listener, map, "pip", "install", "--upgrade", "skencli");
		// execute(listener, map, script, "--org_id", "5fb63d23-ebfd-40a9-972c-03d407af6102", "--app_id", "27438a4b-f745-460c-8f98-27e9c03f9298");
		execute(listener, map, script, "--org_id", orgId, "--app_id", appId);

	}

    @Symbol("greet")
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
	public static int execute(TaskListener listener, Map<String, String> map, String... arguments) {
		listener.getLogger().print("Executing skencli command: ");
		for (String command : arguments) {
			listener.getLogger().print(command);
			listener.getLogger().print(" ");
		}
		listener.getLogger().println();

		ProcessBuilder pb = new ProcessBuilder(arguments);
		Map<String, String> env = pb.environment();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			env.put(entry.getKey(), entry.getValue());
		}
		Process p = null;
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

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			p.destroy();
		}
		System.out.println(p.exitValue());
		return p.exitValue();
	}
    
}
