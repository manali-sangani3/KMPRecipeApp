package com.example.recipeapp.di

import AddRecipeUseCase
import AddRecipeViewModel
import DeleteRecipeUseCase
import EditRecipeUseCase
import GetAllRecipeUseCase
import GetAllTagsUseCase
import GetRecipeUseCase
import HomeViewModel
import RecipeApi
import RecipeDetailViewModel
import RecipeRepository
import RecipeRepositoryImpl
import SortRecipesUseCase
import TagFilterUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val koinModule = module {

    // Http Client
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json { ignoreUnknownKeys = true }
                )
            }
        }
    }

    // API
    single {
        RecipeApi(get())
    }

    // Repository
    single<RecipeRepository> {
        RecipeRepositoryImpl(get())
    }

    // UseCases
    single { GetAllRecipeUseCase(get()) }
    single { SortRecipesUseCase(get()) }
    single { DeleteRecipeUseCase(get()) }
    single { TagFilterUseCase(get()) }
    single { GetAllTagsUseCase(get()) }
    single { AddRecipeUseCase(get()) }
    single { EditRecipeUseCase(get()) }
    single { GetRecipeUseCase(get()) }

    // ViewModels
    factory {
        HomeViewModel(
            getAllRecipeUseCase = get(),
            sortRecipesUseCase = get(),
            deleteRecipeUseCase = get(),
            getRecipesByTagUseCase = get(),
            getAllTagsUseCase = get()
        )
    }
    factory {
        AddRecipeViewModel(
            addRecipeUseCase = get(),
            editRecipeUseCase = get()
        )
    }
    factory {
        RecipeDetailViewModel(
            getRecipeUseCase = get()
        )
    }
}