package ch1

import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun main() {
    // ex1
    var a = 10
    var list: MutableList<Int> = mutableListOf()

    //ex2
    val account = BankAccount()
    println(account.balance)
    account.deposit(100.0)
    println(account.balance)
    account.withdraw(50.0)
    println(account.balance)

    //ex3
    sumByThread()
    runBlocking {
        sumByCoroutine()
    }
    sumSync()

    //코틀린에서 가변성 제한하기
    //1. 읽기 전용 프로퍼티 (val)
    읽기_전용_프로퍼티이지만_변경_가능한_방법()
    val_프로퍼티지만_변경되는_경우()

    calculateExample()

    //plusList
    plusList()
}

class BankAccount {
    var balance = 0.0
        private set

    fun deposit(depositAmount: Double) {
        balance += depositAmount
    }

    @Throws(InsufficientFunds::class)
    fun withdraw(withdrawAmount: Double) {
        if (balance < withdrawAmount) {
            throw InsufficientFunds()
        }

        balance -= withdrawAmount
    }
}

class InsufficientFunds : Exception()

fun sumByThread() {
    var num = 0
    for (i in 1..1000) {
        thread {
            Thread.sleep(10)
            num += 1
        }
    }
    Thread.sleep(5000)
    println(num)
}

suspend fun sumByCoroutine() {
    var num = 0
    coroutineScope {
        for (i in 1..1000) {
            launch {
                delay(10)
                num += 1
            }
        }
    }
    println(num)
}

fun sumSync() {
    val lock = Any()
    var num = 0
    for (i in 1..1000) {
        thread {
            Thread.sleep(10)
            synchronized(lock) {
                num += 1
            }
        }
    }

    Thread.sleep(1000)
    println(num)
}

fun readOnlyProperty() {
    val a = 10
    //a = 20 error
}

fun `읽기_전용_프로퍼티이지만_변경_가능한_방법`() {
    val list = mutableListOf(1, 2, 3)
    list.add(4)

    println(list)
}

//val_프로퍼티지만_변경되는_경우
var name: String = "Marcin"
var surname: String = "Moskala"
val fullName
    get() = "$name $surname"

fun `val_프로퍼티지만_변경되는_경우`() {
    println(fullName)
    name = "Maja"
    println(fullName)
}

fun calculate(): Int {
    print("Calculating...")
    return 42
}

val fizz = calculate()
val buzz
    get() = calculate()

fun calculateExample() {
    println(fizz)
    println(fizz)
    println(buzz)
    println(buzz)
}

interface Element {
    var active: Boolean
}

class ActualElement: Element {
    override var active: Boolean = false
}

fun plusList() {
    val list1: MutableList<Int> = mutableListOf()
    var list2: List<Int> = listOf()

    list1.add(1)
    list2 = list2 + 1

    println(list1)
    println(list2)

    list1.plusAssign(1)
    list2.plus(1)
    list1 += 1
    list2 += 1

    println(list1)
    println(list2)
}