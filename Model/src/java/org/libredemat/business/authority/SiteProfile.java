package org.libredemat.business.authority;

/**
 * Represents a access profile at the site level.
 *
 * @author bor@zenexity.fr
 */
public enum SiteProfile {

    /** An user that can access the site but has no specific right */
    AGENT("Agent"),
    /** A site administrator, can manage agents, categories, ... */
    ADMIN("Admin");

    private String name;

    private SiteProfile(String name) {
        this.name = name;
    }

    /**
     * A vector of all possible {@link SiteProfile site profiles}.
     * @deprecated only for backward, use values() instead
     */
    public static final SiteProfile[] allSiteProfiles = values();

    @Override
    public String toString() {
        return name;
    }

    public static SiteProfile forString(String name) {
        for (SiteProfile s : allSiteProfiles)
            if (s.name.equals(name)) return s;
        return null;
    }
}
