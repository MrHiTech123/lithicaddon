package com.redstoneguy10ls.lithicaddon.common.fluids;

import java.util.Locale;

public enum lithicMetals {
    PEWTER(0xFFE9EAEC),
    IGNEOUS(0xFF829789);
    private final String id;
    private final int color;

    lithicMetals(int color)
    {
        this.id = name().toLowerCase(Locale.ROOT);
        this.color = color;
    }
    public String getId()
    {
        return id;
    }

    public int getColor()
    {
        return color;
    }
}
