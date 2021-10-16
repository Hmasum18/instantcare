package com.memoryleak.instantcare.dagger.module;

import com.memoryleak.instantcare.dagger.anotation.AppScope;
import com.memoryleak.instantcare.service.api.ApiEndPoints;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @AppScope
    static InstantCareApi provideNasaSSCApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .followRedirects(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return new InstantCareApi(retrofit);
    }


    public static class InstantCareApi {
        private final Retrofit retrofit;

        public InstantCareApi(Retrofit retrofit) {
            this.retrofit = retrofit;
        }

        public Retrofit getRetrofit() {
            return retrofit;
        }

        public <T> T create(Class<T> tClass) {
            return retrofit.create(tClass);
        }
    }

}
