package world.ntdi.jaeda.discord;

import java.awt.*;
import java.time.LocalDate;

public class JaedaWebhook extends DiscordWebhook {
    public JaedaWebhook(final String p_url, final String p_username, final String p_uuid, final String p_itemName) {
        super(p_url);

        final EmbedObject embedObject = new EmbedObject();
        embedObject.setColor(Color.ORANGE);
        embedObject.setThumbnail("https://mc-heads.net/body/" + p_uuid + ".png");

        embedObject.setTitle(p_username + " Duplicated an Item");
        embedObject.addField("Item", p_itemName, true);
        embedObject.addField("Time", LocalDate.now().toString(), true);

        embedObject.setFooter("Inventory Duplication Logger", null);

        super.addEmbed(embedObject);
    }
}
