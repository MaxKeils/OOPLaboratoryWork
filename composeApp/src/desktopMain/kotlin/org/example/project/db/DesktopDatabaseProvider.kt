package org.example.project.db

import org.example.project.models.Action
import org.example.project.models.Car

class DesktopDatabaseProvider : DatabaseProvider {
    override suspend fun getAllCars(): List<Car> {
        return DatabaseFactory.dbQuery {
            CarDao.getAllCars()
        }
    }

    override suspend fun getAllActions(): List<Action> {
        return DatabaseFactory.dbQuery {
            CarDao.getAllActions()
        }
    }


    override suspend fun insertCar(car: Car): Int {
        return DatabaseFactory.dbQuery {
            CarDao.insertCar(car)
        }
    }

    override suspend fun deleteCarById(carId: Int) {
        DatabaseFactory.dbQuery {
            CarDao.deleteCar(carId)
        }
    }

    override suspend fun updateCar(newCar: Car) {
        DatabaseFactory.dbQuery {
            CarDao.updateCar(newCar)
        }
    }

    override suspend fun insertAction(car: Car, onActionInsert: (Action) -> Unit) {
        DatabaseFactory.dbQuery {
            CarDao.insertAction(car) {
                onActionInsert(it)
            }
        }
    }

}