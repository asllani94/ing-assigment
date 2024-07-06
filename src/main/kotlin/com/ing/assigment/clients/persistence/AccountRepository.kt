package com.ing.assigment.clients.persistence

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface AccountRepository : CrudRepository<AccountEntity, UUID>
