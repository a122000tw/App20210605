package com.example.app_pokemon.model

data class Pokemon(
    val form_name: String,
    val form_names: List<Any>,
    val form_order: Int,
    val id: Int,
    val is_battle_only: Boolean,
    val is_default: Boolean,
    val is_mega: Boolean,
    val name: String,
    val names: List<Any>,
    val order: Int,
    val pokemon: PokemonX,
    val sprites: Sprites,
    val types: List<Type>,
    val version_group: VersionGroup
)