package com.gildedrose;

class GildedRose {
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String CONJURED = "Conjured Mana Cake";
    public static final int SULFURAS_QUALITY = 80;
    public static final int MAX_QUALITY = 50;

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItemQuality(item);
            updateItemSellIn(item);
            handleExpiredItem(item);
        }
    }

    private void updateItemQuality(Item item) {
        switch (item.name) {
            case SULFURAS:
                // Legendary item - quality never changes
                item.quality = SULFURAS_QUALITY;
                break;

            case AGED_BRIE:
                increaseQuality(item, 1);
                break;

            case BACKSTAGE_PASSES:
                updateBackstagePassQuality(item);
                break;

            case CONJURED:
                decreaseQuality(item, 2);
                break;

            default:
                decreaseQuality(item, 1);
                break;
        }
    }

    private void updateBackstagePassQuality(Item item) {
        if (item.sellIn <= 0) {
            return; // Quality drops to 0 after concert (handled in handleExpiredItem)
        }

        int qualityIncrease = 1;
        if (item.sellIn <= 5) {
            qualityIncrease = 3;
        } else if (item.sellIn <= 10) {
            qualityIncrease = 2;
        }

        increaseQuality(item, qualityIncrease);
    }

    private void updateItemSellIn(Item item) {
        if (item.name.equals(SULFURAS)) {
            item.sellIn = 0;
        } else {
            item.sellIn--;
        }
    }

    private void handleExpiredItem(Item item) {
        if (item.sellIn >= 0) {
            return; // Not expired
        }

        switch (item.name) {
            case SULFURAS:
                // Legendary item - never expires
                break;

            case AGED_BRIE:
                // Aged Brie item - never expires
                break;

            case BACKSTAGE_PASSES:
                item.quality = 0; // Concert has passed
                break;

            case CONJURED:
                decreaseQuality(item, 2);
                break;

            default:
                decreaseQuality(item, 1);
                break;
        }
    }

    private void increaseQuality(Item item, int amount) {
        item.quality = Math.min(MAX_QUALITY, item.quality + amount);
    }

    private void decreaseQuality(Item item, int amount) {
        item.quality = Math.max(0, item.quality - amount);
    }
}
