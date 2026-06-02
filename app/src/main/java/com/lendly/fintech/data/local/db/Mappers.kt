package com.lendly.fintech.data.local.db

import com.lendly.fintech.data.local.db.entities.LoanEntity
import com.lendly.fintech.data.local.db.entities.TransactionEntity
import com.lendly.fintech.data.local.db.entities.UserEntity
import com.lendly.fintech.data.model.Loan
import com.lendly.fintech.data.model.LoanStatus
import com.lendly.fintech.data.model.Transaction
import com.lendly.fintech.data.model.TransactionType
import com.lendly.fintech.data.model.User

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
