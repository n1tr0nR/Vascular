package dev.rbn.vascular.api.monitor;

import dev.rbn.vascular.api.monitor.draw.DrawTextCommand;
import dev.rbn.vascular.api.monitor.draw.DrawTextureCommand;
import dev.rbn.vascular.api.monitor.draw.FillCommand;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class MonitorContext {
    public static final Vector2i MONITOR_SIZE = new Vector2i(140, 100);

    private final List<MonitorDrawCommand> commands = new ArrayList<>();

    public void drawText(Text text, int x, int y, int color, int layer){
        commands.add(new DrawTextCommand(text, x, y, color, layer));
    }

    public void drawTexture(Identifier texture, int x, int y, int width, int height, int layer){
        commands.add(new DrawTextureCommand(texture, x, y, width, height, layer));
    }

    public void fill(int x1, int y1, int x2, int y2, int color, int layer) {
        commands.add(new FillCommand(x1, y1, x2, y2, color, layer));
    }

    public List<MonitorDrawCommand> getCommands() {
        return commands;
    }
}
