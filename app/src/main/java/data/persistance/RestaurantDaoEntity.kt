package data.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class RestaurantDaoEntity(
    //since the Json file there is no ID i will use name as Id
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String
)
