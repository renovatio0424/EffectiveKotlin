package ch1

//상태를 정의할 때는 변수와 프로퍼티의 스코프를 최소화하는 것이 좋습니다.

private val a = 1
private fun fizz() {
    val b = 2
    println(a + b)
}

private val buzz2 = {
    val c = 3
    println(a + c)
}


private fun example1() {
    val users: List<User> = listOf(
        User("jungwon"),
        User("saeeun")
    )
    // 나쁜 예
    var user: User
    for (i in users.indices) {
        user = users[i]
        print("User at $i is $user")
    }

// 조금 더 좋은 예
    for (i in users.indices) {
        val user = users[i]
        print("User at $i is $user")
    }

// 제일 좋은 예
    for ((i, user) in users.withIndex()) {
        print("User at $i is $user")
    }
}

//private fun example2() {
//    //나쁜예
//    val user: User
//    val hasValue = false
//    if (hasValue) {
//        user = getValue()
//    } else {
//        user = User()
//    }
//
//    //조금 더 좋은 예
//    val user: User = if (hasValue) {
//        getValue()
//    } else {
//        User()
//    }
//}

fun printPrimeNumbers() {
    var numbers = (2..100).toList()
    val primes = mutableListOf<Int>()
    while (numbers.isNotEmpty()) {
        val prime = numbers.first()
        primes.add(prime)
        numbers = numbers.filter { it % prime != 0 }
    }

    println(primes)
}

fun printPrimeImprovement() {
    val primes: Sequence<Int> = sequence {
        var numbers = generateSequence(2) { it + 1 } // 2, 3, 4, 5, ....

        while (true) {
            val prime = numbers.first()
            println("1 prime: $prime")
            yield(prime)
            numbers = numbers.drop(1)
                .filter {
                    println("2 prime: $prime, numbers: $it")
                    it % prime != 0
                }
                .map {
                    println("3 prime: $prime, numbers: $it")
                    it
                }
        }
    }
    println(primes.take(10).toList())
}

fun printPrimeError() {
    val primes: Sequence<Int> = sequence {
        var numbers = generateSequence(2) { it + 1 }

        var prime: Int
        while (true) {
            prime = numbers.first()
            println("1 prime: $prime")
            yield(prime)
            numbers = numbers.drop(1)
                .filter {
                    println("2 prime: $prime, numbers: $it")
                    it % prime != 0
                }
                .map {
                    println("3 prime: $prime, numbers: $it")
                    it
                }
        }
    }
    println(primes.take(10).toList())
}

fun main() {
    printPrimeNumbers()
    printPrimeImprovement()
//    printPrimeError()
}