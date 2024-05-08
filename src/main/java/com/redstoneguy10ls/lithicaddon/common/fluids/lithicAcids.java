package com.redstoneguy10ls.lithicaddon.common.fluids;

import java.util.Locale;

public enum lithicAcids {
    NITRIC(0x00494B4C),
    SILVER_HALIDE(0xFFB6B2B2);
    private final String id;
    private final int color;

    lithicAcids(int color)
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
