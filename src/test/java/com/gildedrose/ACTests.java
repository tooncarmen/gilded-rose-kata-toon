package com.gildedrose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ACTests {

    private GildedRose app;

    @BeforeEach
    void setUp() {
        Item[] items = new Item[]{
            new Item("Blind Lemon", 3, 10), //
            new Item("+5 Dexterity Vest", 10, 20), //
            new Item("Aged Brie", 2, 0), //
            new Item("Elixir of the Mongoose", 5, 7), //
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            new Item("Conjured Mana Cake", 3, 6)};

        app = new GildedRose(items);
    }

    @Test
    void regularItemLifeCycleTest() {
        int[] sellIns = new int[]{2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8};
        int[] qualities = new int[]{9, 8, 7, 5, 3, 1, 0, 0};


        for (int i = 0; i < 8; i++) {
            app.updateQuality();

            System.out.println(app.items[0]);
            assertEquals(sellIns[i], app.items[0].sellIn);
            assertEquals(qualities[i], app.items[0].quality);
        }
    }

    @Test
    void agedBrieItemLifeCycleTest() {
        int[] sellIns = new int[]{1, 0, -1, -2, -3, -4, -5, -6, -7, -8};
        int[] qualities = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

        for (int i = 0; i < 8; i++) {
            app.updateQuality();

            System.out.println(app.items[2]);
            assertEquals(sellIns[i], app.items[2].sellIn);
            assertEquals(qualities[i], app.items[2].quality);
        }
    }

    @Test
    void sulfurasItemLifeCycleTest() {
        int[] sellIns = new int[]{0, 0, 0, 0};
        int[] qualities = new int[]{80, 80, 80, 80};

        for (int i = 0; i < 4; i++) {
            app.updateQuality();

            System.out.println(app.items[4]);
            assertEquals(sellIns[i], app.items[4].sellIn);
            assertEquals(qualities[i], app.items[4].quality);
        }
    }

    @Test
    void sulfurasItemLifeCycleTest_bis() {
        int[] sellIns = new int[]{0, 0, 0, 0};
        int[] qualities = new int[]{80, 80, 80, 80};

        for (int i = 0; i < 4; i++) {
            app.updateQuality();

            System.out.println(app.items[5]);
            assertEquals(sellIns[i], app.items[5].sellIn);
            assertEquals(qualities[i], app.items[5].quality);
        }
    }

    @Test
    void backStageItemLifeCycleTest() {
        int[] sellIns = new int[]{14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1};
        int[] qualities = new int[]{21, 22, 23, 24, 25, 27, 29, 31, 33, 35, 38, 41, 44, 47, 50, 0};


        for (int i = 0; i < 16; i++) {
            app.updateQuality();

            System.out.println(app.items[6]);
            assertEquals(sellIns[i], app.items[6].sellIn);
            assertEquals(qualities[i], app.items[6].quality);
        }
    }

    @Test
    void conjuredItemLifeCycleTest() {
        int[] sellIns = new int[]{2, 1, 0, -1, -2};
        int[] qualities = new int[]{4, 2, 0, 0, 0};


        for (int i = 0; i < 5; i++) {
            app.updateQuality();

            System.out.println(app.items[9]);
            assertEquals(sellIns[i], app.items[9].sellIn);
            assertEquals(qualities[i], app.items[9].quality);
        }
    }

    @Test
    void itemQualityLimitTest() {
        for (int i = 0; i < 100; i++) {
            app.updateQuality();
        }

        assertEquals(0, app.items[0].quality); // Blind Lemon
        assertEquals(0, app.items[1].quality); // +5 Dexterity Vest
        assertEquals(50, app.items[2].quality); // Aged Brie
        assertEquals(0, app.items[3].quality); // Elixir of the Mongoose
        assertEquals(80, app.items[4].quality); // Sulfuras, Hand of Ragnaros
        assertEquals(80, app.items[5].quality); // Sulfuras, Hand of Ragnaros
        assertEquals(0, app.items[6].quality); // Backstage passes to a TAFKAL80ETC concert
        assertEquals(0, app.items[7].quality); // Backstage passes to a TAFKAL80ETC concert
        assertEquals(0, app.items[8].quality); // Backstage passes to a TAFKAL80ETC concert
        assertEquals(0, app.items[9].quality); // Conjured Mana Cake
    }
}
