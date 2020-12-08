// CHECKSTYLE:OFF

package ai.sken.jenkins.plugins;

import org.jvnet.localizer.Localizable;
import org.jvnet.localizer.ResourceBundleHolder;
import org.kohsuke.accmod.Restricted;


/**
 * Generated localization support class.
 * 
 */
@SuppressWarnings({
    "",
    "PMD",
    "all"
})
@Restricted(org.kohsuke.accmod.restrictions.NoExternalUse.class)
public class Messages {

    /**
     * The resource bundle reference
     * 
     */
    private final static ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);

    /**
     * Key {@code SkenCLIBuilder.DescriptorImpl.DisplayName}: {@code
     * Sken.ai}.
     * 
     * @return
     *     {@code Sken.ai}
     */
    public static String SkenCLIBuilder_DescriptorImpl_DisplayName() {
        return holder.format("SkenCLIBuilder.DescriptorImpl.DisplayName");
    }

    /**
     * Key {@code SkenCLIBuilder.DescriptorImpl.DisplayName}: {@code
     * Sken.ai}.
     * 
     * @return
     *     {@code Sken.ai}
     */
    public static Localizable _SkenCLIBuilder_DescriptorImpl_DisplayName() {
        return new Localizable(holder, "SkenCLIBuilder.DescriptorImpl.DisplayName");
    }

    /**
     * Key {@code SkenCLIBuilder.DescriptorImpl.errors.missingId}: {@code
     * Required}.
     * 
     * @return
     *     {@code Required}
     */
    public static String SkenCLIBuilder_DescriptorImpl_errors_missingId() {
        return holder.format("SkenCLIBuilder.DescriptorImpl.errors.missingId");
    }

    /**
     * Key {@code SkenCLIBuilder.DescriptorImpl.errors.missingId}: {@code
     * Required}.
     * 
     * @return
     *     {@code Required}
     */
    public static Localizable _SkenCLIBuilder_DescriptorImpl_errors_missingId() {
        return new Localizable(holder, "SkenCLIBuilder.DescriptorImpl.errors.missingId");
    }

    /**
     * Key {@code SkenCLIBuilder.DescriptorImpl.warnings.tooShort}: {@code
     * Isn't the ID too short?}.
     * 
     * @return
     *     {@code Isn't the ID too short?}
     */
    public static String SkenCLIBuilder_DescriptorImpl_warnings_tooShort() {
        return holder.format("SkenCLIBuilder.DescriptorImpl.warnings.tooShort");
    }

    /**
     * Key {@code SkenCLIBuilder.DescriptorImpl.warnings.tooShort}: {@code
     * Isn't the ID too short?}.
     * 
     * @return
     *     {@code Isn't the ID too short?}
     */
    public static Localizable _SkenCLIBuilder_DescriptorImpl_warnings_tooShort() {
        return new Localizable(holder, "SkenCLIBuilder.DescriptorImpl.warnings.tooShort");
    }

}
