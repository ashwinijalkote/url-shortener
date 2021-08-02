package com.ashwini.urlshortener.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BaseConversionTest {
    @Test
    public void testIdToShortString() {
        assertEquals("qi", BaseConversion.idToShortString(1000L));
    }

    @Test
    public void testIdToShortString1() {
        assertEquals("A", BaseConversion.idToShortString(26L));
    }

    @Test
    public void testShortStringToId() {
        assertEquals(1000, BaseConversion.shortStringToId("qi"));
    }

    @Test
    public void testShortStringToId1() {
        assertEquals(62008, BaseConversion.shortStringToId("qii"));
    }
}