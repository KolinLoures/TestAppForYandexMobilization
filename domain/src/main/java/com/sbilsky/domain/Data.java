package com.sbilsky.domain;

import com.sbilsky.data.storage.usage.favorite_and_history.FavoriteAndHistorySqlDbImpl;
import com.sbilsky.data.storage.usage.languages.LanguagesSqlDbImpl;
import com.sbilsky.domain.favorite_and_history.FavoriteAndHistoryDataImpl;
import com.sbilsky.domain.favorite_and_history.IFavoriteAndHistoryData;
import com.sbilsky.domain.languages.ILanguagesData;
import com.sbilsky.domain.languages.LanguagesDataImpl;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class Data {
    public static IFavoriteAndHistoryData favoriteAndHistory() {
        return new FavoriteAndHistoryDataImpl(new FavoriteAndHistorySqlDbImpl());
    }

    public static ILanguagesData languages() {
        return new LanguagesDataImpl(new LanguagesSqlDbImpl());
    }
}
