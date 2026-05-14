package com.gildedrose;

import java.util.HashMap;
import java.util.Map;

class GildedRose {
    private static final int MAX_QUALITY = 50;
    private static final int MIN_QUALITY = 0;
    private static final int SULFURAS_QUALITY = 80;

    Item[] items;
    private final Map<String, ItemUpdateStrategy> strategies;

    public GildedRose(Item[] items) {
        this.items = items;
        this.strategies = initializeStrategies();
    }

    private Map<String, ItemUpdateStrategy> initializeStrategies() {
        Map<String, ItemUpdateStrategy> map = new HashMap<>();
        map.put("Sulfuras, Hand of Ragnaros", new SulfurasStrategy());
        map.put("Aged Brie", new AgedBrieStrategy());
        map.put("Backstage passes to a TAFKAL80ETC concert", new BackstagePassStrategy());
        map.put("Conjured Mana Cake", new ConjuredStrategy());
        return map;
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemUpdateStrategy strategy = strategies.getOrDefault(item.name, new StandardStrategy());
            strategy.update(item);
        }
    }

    // Strategy Interface
    interface ItemUpdateStrategy {
        void update(Item item);
    }

    // Sulfuras Strategy - Legendary item never changes
    static class SulfurasStrategy implements ItemUpdateStrategy {
        @Override
        public void update(Item item) {
            item.quality = SULFURAS_QUALITY;
            // sellIn never changes for Sulfuras
        }
    }

    // Aged Brie Strategy - Increases in quality over time
    static class AgedBrieStrategy implements ItemUpdateStrategy {
        @Override
        public void update(Item item) {
            item.sellIn--;

            item.quality = Math.min(MAX_QUALITY, item.quality + 1);
        }
    }

    // Backstage Pass Strategy - Complex quality rules based on sellIn
    static class BackstagePassStrategy implements ItemUpdateStrategy {
        private static final int FIRST_THRESHOLD = 10;
        private static final int SECOND_THRESHOLD = 5;

        @Override
        public void update(Item item) {
            item.sellIn--;

            if (item.sellIn < 0) {
                item.quality = 0; // Concert has passed
            } else if (item.sellIn < SECOND_THRESHOLD) {
                item.quality = Math.min(MAX_QUALITY, item.quality + 3);
            } else if (item.sellIn < FIRST_THRESHOLD) {
                item.quality = Math.min(MAX_QUALITY, item.quality + 2);
            } else {
                item.quality = Math.min(MAX_QUALITY, item.quality + 1);
            }
        }
    }

    // Conjured Strategy - Degrades twice as fast as normal items
    static class ConjuredStrategy implements ItemUpdateStrategy {
        @Override
        public void update(Item item) {
            item.sellIn--;

            int qualityDecrease = item.sellIn < 0 ? 4 : 2;
            item.quality = Math.max(MIN_QUALITY, item.quality - qualityDecrease);
        }
    }

    // Standard Strategy - Regular items that degrade normally
    static class StandardStrategy implements ItemUpdateStrategy {
        @Override
        public void update(Item item) {
            item.sellIn--;

            int qualityDecrease = item.sellIn < 0 ? 2 : 1;
            item.quality = Math.max(MIN_QUALITY, item.quality - qualityDecrease);
        }
    }
}
