package com.ing.assigment.config

import com.ing.assigment.clients.persistence.AccountEntity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableJdbcRepositories(basePackages = ["com.ing.assigment.clients.persistence"])
class JdbcConfiguration : AbstractJdbcConfiguration() {

    @Bean
    fun namedParameterJdbcOperations(dataSource: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(dataSource)
    }

    @Bean
    @Primary
    fun transactionManager(dataSource: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean
    fun beforeConvertCallback(): BeforeConvertCallback<AccountEntity> {
        return BeforeConvertCallback<AccountEntity> { account: AccountEntity ->
            if (account.id == null) {
                account.id = UUID.randomUUID()
            }
            account
        }
    }
}
