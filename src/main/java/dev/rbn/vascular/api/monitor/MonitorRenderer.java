package dev.rbn.vascular.api.monitor;

import java.util.Comparator;
import java.util.List;

public class MonitorRenderer {
    public static void render(MonitorContext context, MonitorRenderContext renderCtx){
        List<MonitorDrawCommand> commands = context.getCommands();
        commands.sort(Comparator.comparingInt(c -> c.layer));
        for (MonitorDrawCommand cmd : commands){
            cmd.render(renderCtx);
        }
    }
}
