package org.exodusstudio.arcana.common.data.theorie;

import java.util.LinkedHashMap;

public class TheoryRegistry {
    private static final LinkedHashMap<Integer, Theory> THEORIES = new LinkedHashMap<>();

    public static void registerTheory(Theory theory)
    {
        THEORIES.put(theory.getId(), theory);
    }

    public static LinkedHashMap<Integer, Theory> getAllTheories()
    {
        return THEORIES;
    }

    public static Theory getTheory(int theoryId)
    {
        return THEORIES.get(theoryId);
    }

    public static void RegisterTheories()
    {
        registerTheory(new DirtTheory());
        registerTheory(new StoneTheory());
        registerTheory(new NhilTheory());
    }

    public static int TheoryAmount()
    {
        return THEORIES.size();
    }
}
