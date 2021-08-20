package com.mkamilmistar.gold_market.data.model.response

import com.mkamilmistar.gold_market.data.model.entity.Pocket

data class CustomerWithPocketsResponse (
  val pocketList: List<Pocket>
)
