/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package sti.com.livefeedback.data.local.db.dao;

import androidx.room.*;
import io.reactivex.Single;
import sti.com.livefeedback.data.model.db.Cart;
import sti.com.livefeedback.data.model.db.User;

import java.util.List;

/**
 * Created by amitshekhar on 07/07/17.
 */

@Dao
public interface CartDao {

    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM cartlist WHERE name LIKE :name LIMIT 1")
    Single<Cart> findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cart cart);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Cart> carts);

    @Query("SELECT * FROM cartlist")
    List<Cart> loadAll();

    @Query("SELECT * FROM cartlist WHERE id IN (:cartIds)")
    List<Cart> loadAllByIds(List<Integer> cartIds);
}
