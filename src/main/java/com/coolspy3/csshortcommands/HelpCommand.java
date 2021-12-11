package com.coolspy3.csshortcommands;

import com.coolspy3.csmodloader.network.SubscribeToPacketStream;
import com.coolspy3.cspackets.datatypes.MCColor;
import com.coolspy3.cspackets.packets.ClientChatSendPacket;
import com.coolspy3.util.ModUtil;

public class HelpCommand
{

    public static final String justABunchOfDashes = "-----------------------------";

    @SubscribeToPacketStream
    public boolean register(ClientChatSendPacket event)
    {
        if (event.msg.matches("/schelp( .*)?"))
        {
            ModUtil.sendMessage(MCColor.BLUE + justABunchOfDashes);
            ModUtil.sendMessage(MCColor.YELLOW + "/sc set <trigger> <command>" + MCColor.AQUA
                    + " - Sets the message \"<trigger>\" to instead type \"<command>\"");
            ModUtil.sendMessage(MCColor.YELLOW + "/sc remove <trigger>" + MCColor.AQUA
                    + " - Removes the specified trigger from the command list");
            ModUtil.sendMessage(MCColor.YELLOW + "/sc list" + MCColor.AQUA
                    + " - Lists the current triggers and their respective commands");
            ModUtil.sendMessage(
                    MCColor.YELLOW + "/sc help" + MCColor.AQUA + " - Prints this help message");
            ModUtil.sendMessage(
                    MCColor.YELLOW + "/schelp" + MCColor.AQUA + " - Prints this help message");
            ModUtil.sendMessage(MCColor.BLUE + justABunchOfDashes);

            return true;
        }

        return false;
    }

}
