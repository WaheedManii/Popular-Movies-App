package com.example.waheedmanii.popularmovies.Provider;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by Waheed Manii on 25-Aug-16.
 */


@SimpleSQLConfig(
        name = "MovieProvider",
        authority = "just.some.movie_provider.authority",
        database = "movies.db",
        version = 1)

public class MovieProviderConfig implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
