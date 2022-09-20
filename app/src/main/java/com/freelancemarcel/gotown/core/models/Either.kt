package com.freelancemarcel.gotown.core.models

sealed class Either<out L, out R> {
    class Left<out L>(val value: L) : Either<L, Nothing>() {
        override fun toString(): String = "Left $value"
    }

    class Right<out R>(val value: R) : Either<Nothing, R>() {
        override fun toString(): String = "Right $value"
    }

    public inline fun <C> fold(ifLeft: (L) -> C, ifRight: (R) -> C): C = when (this) {
        is Right -> ifRight(value)
        is Left -> ifLeft(value)
    }
}