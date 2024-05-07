package com.redstoneguy10ls.lithicaddon.common.fluids;

import java.util.Locale;

public enum lithicGlass {
    VOLCANIC(0x3a2269),
    HEMATITIC(0xe18957),
    SILICA(0x778899),
    OLIVINE(0x9AB972);
    private final String id;
    private final int color;

    lithicGlass(int color)
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
