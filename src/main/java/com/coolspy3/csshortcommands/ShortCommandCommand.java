package com.coolspy3.csshortcommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coolspy3.csmodloader.network.PacketHandler;
import com.coolspy3.csmodloader.network.SubscribeToPacketStream;
import com.coolspy3.csmodloader.util.Utils;
import com.coolspy3.cspackets.datatypes.MCColor;
import com.coolspy3.cspackets.packets.ClientChatSendPacket;
import com.coolspy3.util.ModUtil;

public class ShortCommandCommand
{

    public static final String addRegex = "/sc set ([a-zA-Z0-9_\\-\\/]+) (.+)";
    public static final Pattern addPattern = Pattern.compile(addRegex);
    public static final String removeRegex = "/sc remove ([a-zA-Z0-9_\\-\\/]+)";
    public static final Pattern removePattern = Pattern.compile(removeRegex);

    @SubscribeToPacketStream
    public boolean register(ClientChatSendPacket event)
    {
        String msg = event.msg;
        if (msg.matches("/sc( .*)?"))
        {
            if (msg.matches("/sc set( .*)?"))
            {
                Matcher addMatcher = addPattern.matcher(msg);
                if (addMatcher.matches())
                {
                    String trigger = addMatcher.group(1);
                    String command = addMatcher.group(2);
                    Config.getInstance().shortcuts.put(trigger, command);
                    Utils.reporting(Config::save);
                    ModUtil.sendMessage(MCColor.AQUA + "Typing: \"" + trigger
                            + "\" will now type: \"" + command + "\"");
                }
                else
                {
                    ModUtil.sendMessage(MCColor.RED + "Usage: /sc set <trigger> <command>");
                }
            }
            else if (msg.matches("/sc remove( .*)?"))
            {
                Matcher removeMatcher = removePattern.matcher(msg);
                if (removeMatcher.matches())
                {
                    String trigger = removeMatcher.group(1);
                    Config.getInstance().shortcuts.remove(trigger);
                    Utils.reporting(Config::save);
                    ModUtil.sendMessage(MCColor.AQUA + "Removed short command for trigger: \""
                            + trigger + "\"");
                }
                else
                {
                    ModUtil.sendMessage(MCColor.RED + "Usage: /sc remove <trigger>");
                }
            }
            else if (msg.matches("/sc list( .*)?"))
            {
                ModUtil.sendMessage(MCColor.AQUA + "Current Short Commands:");
                if (Config.getInstance().shortcuts.isEmpty())
                {
                    ModUtil.sendMessage(MCColor.AQUA + "<None>");
                }
                else
                {
                    for (String trigger : Config.getInstance().shortcuts.keySet())
                    {
                        String command = Config.getInstance().shortcuts.get(trigger);
                        ModUtil.sendMessage(MCColor.AQUA + "Typing: \"" + trigger
                                + "\" will type: \"" + command + "\"");
                    }
                }
            }
            else if (msg.matches("/sc help( .*)?"))
            {
                PacketHandler.getLocal().dispatch(new ClientChatSendPacket("/schelp"));
            }
            else
            {
                ModUtil.sendMessage(MCColor.RED + "Usage: /sc [set|remove|list|help]");
            }

            return true;
        }

        return false;
    }

}
