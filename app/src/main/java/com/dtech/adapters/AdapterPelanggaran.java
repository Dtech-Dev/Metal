package com.dtech.adapters;

import android.content.Context;

import com.dtech.orm.MtlPelanggan;
import com.dtech.orm.MtlPelanggaran;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample customer for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class AdapterPelanggaran {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Fouls> ITEMS = new ArrayList<Fouls>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Fouls> ITEM_MAP = new HashMap<String, Fouls>();

    public AdapterPelanggaran(Context context
            , List<MtlPelanggaran> mtlPelanggarans){

        for (MtlPelanggaran foul : mtlPelanggarans){
            addItem(new Fouls(foul));
        }

    }

    private static void addItem(Fouls fouls) {
        ITEMS.add(fouls);
        ITEM_MAP.put(fouls.id, fouls);
    }

    /**
     * A dummy item representing a piece of customer.
     */
    public static class Fouls {
        public String id;
        public MtlPelanggan customer;
        public String foulDate;
        public String foulType;
        public String tariff;
        public Integer daya;

        public Fouls(String pelanggaranID, MtlPelanggan customer,
                     String foulDate, String foulType,
                     String tariff, Integer daya) {
            this.id = pelanggaranID;
            this.customer = customer;
            this.foulDate= foulDate;
            this.foulType = foulType;
            this.tariff = tariff;
            this.daya = daya;
        }

        public Fouls(MtlPelanggaran foul) {
            this(foul.getId().toString(), foul.getPelanggan(),
                    foul.getFoulDate(), foul.getFoulType(),
                    foul.getTariff(), foul.getDaya());
        }
    }
}
