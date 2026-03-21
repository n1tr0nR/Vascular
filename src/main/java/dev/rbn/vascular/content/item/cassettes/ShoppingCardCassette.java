package dev.rbn.vascular.content.item.cassettes;

import dev.rbn.vascular.api.monitor.MonitorContext;
import dev.rbn.vascular.api.monitor.MonitorDisplay;
import dev.rbn.vascular.api.monitor.set.cassette.ShoppingCartDisplay;
import dev.rbn.vascular.content.item.CassetteItem;

public class ShoppingCardCassette extends CassetteItem {
    public ShoppingCardCassette(Settings settings) {
        super(settings);
    }

    @Override
    public MonitorDisplay getDisplay() {
        return new ShoppingCartDisplay(new MonitorContext());
    }
}