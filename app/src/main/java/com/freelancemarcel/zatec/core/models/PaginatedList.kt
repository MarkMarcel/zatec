package com.freelancemarcel.zatec.core.models

data class PaginatedList<K, E>(val items: List<E>, val next: K?, val prev: K?)