package world.ntdi.jaeda.config;

public enum ConfigOptions {
    DISCORD_WEBHOOK_URL("discord-webhook-url"),
    BLACKLISTED_ITEMS("blacklist-items");

    private final String p_name;
    ConfigOptions(final String p_pName) {
        p_name = p_pName;
    }

    public String getName() {
        return p_name;
    }
}
