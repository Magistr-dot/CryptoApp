package com.example.cryptoapp.data.database.mapper

import com.example.cryptoapp.data.database.CoinInfoDBModel
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.network.model.CoinInfoJSONContainerDto
import com.example.cryptoapp.data.network.model.CoinNamesListDto
import com.example.cryptoapp.domain.CoinInfo
import com.google.gson.Gson

class CoinMapper {
    fun mapDtoToDbModel(dto: CoinInfoDto): CoinInfoDBModel {
        return CoinInfoDBModel(
            dto.fromSymbol,
            dto.toSymbol,
            dto.price,
            dto.lastUpdate,
            dto.highDay,
            dto.lowDay,
            dto.lastMarket,
            dto.imageUrl
        )
    }

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJSONContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map {
            it.coinNameDto?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDBModel): CoinInfo {
        return CoinInfo(
            dbModel.fromSymbol,
            dbModel.toSymbol,
            dbModel.price,
            dbModel.lastUpdate,
            dbModel.highDay,
            dbModel.lowDay,
            dbModel.lastMarket,
            dbModel.imageUrl
        )
    }
}