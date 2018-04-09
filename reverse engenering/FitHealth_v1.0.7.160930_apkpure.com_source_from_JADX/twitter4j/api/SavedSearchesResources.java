package twitter4j.api;

import twitter4j.ResponseList;
import twitter4j.SavedSearch;
import twitter4j.TwitterException;

public interface SavedSearchesResources {
    SavedSearch createSavedSearch(String str) throws TwitterException;

    SavedSearch destroySavedSearch(long j) throws TwitterException;

    ResponseList<SavedSearch> getSavedSearches() throws TwitterException;

    SavedSearch showSavedSearch(long j) throws TwitterException;
}
