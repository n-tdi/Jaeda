package world.ntdi.jaeda.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import world.ntdi.jaeda.Jaeda;

import java.util.Collections;
import java.util.List;

public class ReloadCommand implements TabExecutor {
    private final Jaeda m_jaeda;

    public ReloadCommand(final Jaeda p_jaeda) {
        m_jaeda = p_jaeda;
    }

    @Override
    public boolean onCommand(final CommandSender p_commandSender, final Command p_command, final String p_s, final String[] p_strings) {
        if (p_commandSender.isOp()) {
            m_jaeda.getConfig().reload();
            p_commandSender.sendMessage(ChatColor.GREEN + "Reload complete.");
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(final CommandSender p_commandSender, final Command p_command, final String p_s, final String[] p_strings) {
        return Collections.singletonList("reload");
    }
}
