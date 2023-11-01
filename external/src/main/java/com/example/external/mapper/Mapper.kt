package com.example.external.mapper

abstract class Mapper<R, M> {
    abstract fun map(response: R): M
    abstract fun map(response: List<R>): List<M>
}