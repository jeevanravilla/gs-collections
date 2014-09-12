/*
 * Copyright 2014 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.impl.multimap.set;

import com.gs.collections.api.collection.MutableCollection;
import com.gs.collections.api.multimap.Multimap;
import com.gs.collections.api.set.MutableSet;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.multimap.AbstractMutableMultimapTestCase;
import com.gs.collections.impl.set.mutable.MultiReaderUnifiedSet;
import com.gs.collections.impl.set.mutable.UnifiedSet;
import com.gs.collections.impl.test.Verify;
import com.gs.collections.impl.tuple.Tuples;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test of {@link com.gs.collections.impl.multimap.list.MultiReaderUnifiedSetMultimap}.
 */
public class MultiReaderUnifiedSetMultimapTest extends AbstractMutableMultimapTestCase
{
    @Override
    public <K, V> MultiReaderUnifiedSetMultimap<K, V> newMultimap()
    {
        return MultiReaderUnifiedSetMultimap.newMultimap();
    }

    @Override
    public <K, V> MultiReaderUnifiedSetMultimap<K, V> newMultimapWithKeyValue(K key, V value)
    {
        MultiReaderUnifiedSetMultimap<K, V> mutableMultimap = this.newMultimap();
        mutableMultimap.put(key, value);
        return mutableMultimap;
    }

    @Override
    public <K, V> MultiReaderUnifiedSetMultimap<K, V> newMultimapWithKeysValues(K key1, V value1, K key2, V value2)
    {
        MultiReaderUnifiedSetMultimap<K, V> mutableMultimap = this.newMultimap();
        mutableMultimap.put(key1, value1);
        mutableMultimap.put(key2, value2);
        return mutableMultimap;
    }

    @Override
    protected <V> MutableCollection<V> createCollection(V... args)
    {
        return MultiReaderUnifiedSet.newSetWith(args);
    }

    @Override
    public <K, V> Multimap<K, V> newMultimap(Pair<K, V>... pairs)
    {
        return MultiReaderUnifiedSetMultimap.newMultimap(pairs);
    }

    @Override
    public <K, V> Multimap<K, V> newMultimapFromPairs(Iterable<Pair<K, V>> inputIterable)
    {
        return MultiReaderUnifiedSetMultimap.newMultimap(inputIterable);
    }

    @Override
    public <K, V> MultiReaderUnifiedSetMultimap<K, V> newMultimapWithKeysValues(
            K key1, V value1,
            K key2, V value2,
            K key3, V value3)
    {
        MultiReaderUnifiedSetMultimap<K, V> mutableMultimap = this.newMultimap();
        mutableMultimap.put(key1, value1);
        mutableMultimap.put(key2, value2);
        mutableMultimap.put(key3, value3);
        return mutableMultimap;
    }

    @Override
    public <K, V> MultiReaderUnifiedSetMultimap<K, V> newMultimapWithKeysValues(
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4)
    {
        MultiReaderUnifiedSetMultimap<K, V> mutableMultimap = this.newMultimap();
        mutableMultimap.put(key1, value1);
        mutableMultimap.put(key2, value2);
        mutableMultimap.put(key3, value3);
        mutableMultimap.put(key4, value4);
        return mutableMultimap;
    }

    @Test
    public void pairIterableConstructorTest()
    {
        Pair<Integer, String> pair1 = Tuples.pair(Integer.valueOf(1), "One");
        Pair<Integer, String> pair2 = Tuples.pair(Integer.valueOf(2), "Two");
        Pair<Integer, String> pair3 = Tuples.pair(Integer.valueOf(3), "Three");
        Pair<Integer, String> pair4 = Tuples.pair(Integer.valueOf(4), "Four");

        Pair<Integer, String> pair11 = Tuples.pair(Integer.valueOf(1), "OneOne");
        Pair<Integer, String> pair22 = Tuples.pair(Integer.valueOf(2), "TwoTwo");
        Pair<Integer, String> pair33 = Tuples.pair(Integer.valueOf(3), "ThreeThree");
        Pair<Integer, String> pair44 = Tuples.pair(Integer.valueOf(4), "FourFour");

        Pair<Integer, String> pair111 = Tuples.pair(Integer.valueOf(1), "One");
        Pair<Integer, String> pair222 = Tuples.pair(Integer.valueOf(2), "Two");
        Pair<Integer, String> pair333 = Tuples.pair(Integer.valueOf(3), "Three");
        Pair<Integer, String> pair444 = Tuples.pair(Integer.valueOf(4), "Four");

        MutableSet<Pair<Integer, String>> testBag = UnifiedSet.<Pair<Integer, String>>newSetWith(pair1, pair2, pair3, pair4, pair11, pair22, pair33, pair44, pair111, pair222, pair333, pair444);

        MultiReaderUnifiedSetMultimap<Integer, String> actual = MultiReaderUnifiedSetMultimap.newMultimap(testBag);

        Assert.assertEquals(UnifiedSet.newSetWith(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)), actual.keysView().toSet());
        Assert.assertEquals(UnifiedSet.newSetWith("One", "OneOne", "One"), actual.get(Integer.valueOf(1)));
        Assert.assertEquals(UnifiedSet.newSetWith("Two", "TwoTwo", "Two"), actual.get(Integer.valueOf(2)));
        Assert.assertEquals(UnifiedSet.newSetWith("Three", "ThreeThree", "Three"), actual.get(Integer.valueOf(3)));
        Assert.assertEquals(UnifiedSet.newSetWith("Four", "FourFour", "Four"), actual.get(Integer.valueOf(4)));
    }

    @Override
    @Test
    public void selectKeysValues()
    {
        MultiReaderUnifiedSetMultimap<String, Integer> multimap = MultiReaderUnifiedSetMultimap.newMultimap();
        multimap.putAll("One", FastList.newListWith(1, 1, 2, 3, 4));
        multimap.putAll("Two", FastList.newListWith(2, 2, 3, 4, 5));
        UnifiedSetMultimap<String, Integer> selectedMultimap = multimap.selectKeysValues((key, value) -> ("Two".equals(key) && (value % 2 == 0)));
        UnifiedSetMultimap<String, Integer> expectedMultimap = UnifiedSetMultimap.newMultimap();
        expectedMultimap.putAll("Two", FastList.newListWith(2, 4));
        Assert.assertEquals(expectedMultimap, selectedMultimap);
        Verify.assertSetsEqual(expectedMultimap.get("Two"), selectedMultimap.get("Two"));
    }

    @Override
    @Test
    public void rejectKeysValues()
    {
        MultiReaderUnifiedSetMultimap<String, Integer> multimap = MultiReaderUnifiedSetMultimap.newMultimap();
        multimap.putAll("One", FastList.newListWith(1, 1, 2, 3, 4));
        multimap.putAll("Two", FastList.newListWith(2, 2, 3, 4, 5));
        UnifiedSetMultimap<String, Integer> rejectedMultimap = multimap.rejectKeysValues((key, value) -> ("Two".equals(key) || (value % 2 == 0)));
        UnifiedSetMultimap<String, Integer> expectedMultimap = UnifiedSetMultimap.newMultimap();
        expectedMultimap.putAll("One", FastList.newListWith(1, 3));
        Assert.assertEquals(expectedMultimap, rejectedMultimap);
        Verify.assertSetsEqual(expectedMultimap.get("One"), rejectedMultimap.get("One"));
    }
}