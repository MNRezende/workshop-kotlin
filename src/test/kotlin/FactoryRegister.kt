class FactoryRegister {
    var pojoregister = PojoRegister()

    fun successRegister() : PojoRegister {
        pojoregister.email = "eve.holt@reqres.in"
        pojoregister.password = "pistoe"
        return pojoregister
    }

    fun userNotFoundRegister() : PojoRegister {
        pojoregister.email = "reinkmakoi@gmai.com"
        pojoregister.password = "reink"
        return pojoregister
    }

    fun unsuccessfulRegister() : PojoRegister {
        val pojoRegister = successRegister()
        pojoRegister.password = null
        return pojoRegister
    }
}