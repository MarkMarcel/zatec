package com.freelancemarcel.gotown.core.models

data class PaginatedList<K, E>(val items: List<E>, val next: K?, val prev: K?)