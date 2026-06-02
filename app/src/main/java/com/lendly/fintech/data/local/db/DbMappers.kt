package com.lendly.fintech.data.local.db

import com.lendly.fintech.data.local.db.entity.LoanEntity
import com.lendly.fintech.data.local.db.entity.TransactionEntity
import com.lendly.fintech.data.local.db.entity.UserEntity
import com.lendly.fintech.data.model.Loan
import com.lendly.fintech.data.model.LoanStatus
import com.lendly.fintech.data.model.Transaction
import com.lendly.fintech.data.model.TransactionType
import com.lendly.fintech.data.model.User

/**
 * Conversiones entre los modelos de dominio y las entidades Room.
 *
 * Los enums se persisten por su `name`; al leer se traducen de forma tolerante: un valor
 * desconocido de [LoanStatus] cae en [LoanStatus.PENDING] y uno de [TransactionType] queda en `null`.
 */

// ---- User ----

fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name,
    lastName = lastName,
    email = email,
    phone = phone,
    creditScore = creditScore,
    profileImage = profileImage,
)

fun UserEntity.toDomain(): User = User(
    id = id,
    name = name,
    lastName = lastName,
    email = email,
    phone = phone,
    creditScore = creditScore,
    profileImage = profileImage,
)

// ---- Loan ----

fun Loan.toEntity(): LoanEntity = LoanEntity(
    id = id,
    amount = amount,
    installments = installments,
    status = status.name,
    dueDate = dueDate,
    interestRate = interestRate,
)

fun LoanEntity.toDomain(): Loan = Loan(
    id = id,
    amount = amount,
    installments = installments,
    status = runCatching { LoanStatus.valueOf(status) }.getOrDefault(LoanStatus.PENDING),
    dueDate = dueDate,
    interestRate = interestRate,
)

// ---- Transaction ----

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    type = type?.name,
    title = title,
    description = description,
    amount = amount,
    currency = currency,
    status = status,
    date = date,
    loanId = loanId,
    referenceNumber = referenceNumber,
)

fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    type = type?.let { runCatching { TransactionType.valueOf(it) }.getOrNull() },
    title = title,
    description = description,
    amount = amount,
    currency = currency,
    status = status,
    date = date,
    loanId = loanId,
    referenceNumber = referenceNumber,
)