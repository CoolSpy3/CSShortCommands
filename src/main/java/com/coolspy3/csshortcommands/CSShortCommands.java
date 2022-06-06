package com.coolspy3.csshortcommands;

import com.coolspy3.csmodloader.mod.Entrypoint;
import com.coolspy3.csmodloader.mod.Mod;
import com.coolspy3.csmodloader.network.PacketHandler;
import com.coolspy3.csmodloader.network.SubscribeToPacketStream;
import com.coolspy3.csmodloader.util.Utils;
import com.coolspy3.cspackets.packets.ClientChatSendPacket;

@Mod(id = "csshortcommands", name = "CSShortCommands",
        description = "Adds the ability to alias commands.", version = "1.0.1",
        dependencies = {"csmodloader:[1.3.1,2)", "cspackets:[1.2.1,2)", "csutils:[1.1.1,2)"})
public class CSShortCommands implements Entrypoint
{

    @SubscribeToPacketStream
    public void init(PacketHandler handler)
    {
        Utils.reporting(Config::load);
        handler.register(this);
        handler.register(new HelpCommand());
        handler.register(new ShortCommandCommand());
    }

    @SubscribeToPacketStream
    public boolean onSendChat(ClientChatSendPacket event)
    {
        if (Config.getInstance().shortcuts.containsKey(event.msg))
        {
            String shortcut = Config.getInstance().shortcuts.get(event.msg);
            ClientChatSendPacket packet = new ClientChatSendPacket(shortcut);
            if (!PacketHandler.getLocal().dispatch(packet))
                PacketHandler.getLocal().sendPacket(packet);

            return true;
        }

        return false;
    }

}
