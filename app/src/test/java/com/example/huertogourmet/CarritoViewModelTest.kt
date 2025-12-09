package com.example.huertogourmet

import com.example.huertogourmet.data.model.Plato
import com.example.huertogourmet.presentation.viewmodel.CarritoViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class CarritoViewModelTest : StringSpec({

    "agregarAlCarrito suma item nuevo y calcula total" {
        val vm = CarritoViewModel()
        val p = Plato(
            id = 1L,
            nombre = "Prueba",
            descripcion = "desc",
            precio = 1000.0,
            imagen = null
        )

        vm.agregarAlCarrito(p, 2)

        val items = vm.items.value
        items.shouldHaveSize(1)
        items.first().cantidad shouldBe 2
        vm.totalPagar.value shouldBe 2000.0
    }

    "agregarAlCarrito suma cantidad si ya existe" {
        val vm = CarritoViewModel()
        val p = Plato(
            id = 1L,
            nombre = "Prueba",
            descripcion = "desc",
            precio = 500.0,
            imagen = null
        )

        vm.agregarAlCarrito(p, 1)
        vm.agregarAlCarrito(p, 1)
        vm.agregarAlCarrito(p, 1)
        vm.agregarAlCarrito(p, 1)

        vm.items.value.first().cantidad shouldBe 4
        vm.totalPagar.value shouldBe 2000.0
    }
})
