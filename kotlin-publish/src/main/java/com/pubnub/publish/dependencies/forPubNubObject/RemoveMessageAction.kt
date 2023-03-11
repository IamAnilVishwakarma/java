package com.pubnub.publish.dependencies.forPubNubObject

import com.pubnub.api.Endpoint
import com.pubnub.api.PubNub
import com.pubnub.api.PubNubError
import com.pubnub.api.PubNubException
import com.pubnub.api.enums.PNOperationType
import com.pubnub.api.models.consumer.message_actions.PNRemoveMessageActionResult
import retrofit2.Call
import retrofit2.Response

/**
 * @see [PubNub.removeMessageAction]
 */
class RemoveMessageAction internal constructor(
    pubnub: PubNub,
    val channel: String,
    val messageTimetoken: Long,
    val actionTimetoken: Long
) : Endpoint<Void, PNRemoveMessageActionResult>(pubnub) {

    override fun validateParams() {
        super.validateParams()
        if (channel.isBlank()) throw PubNubException(PubNubError.CHANNEL_MISSING)
    }

    override fun getAffectedChannels() = listOf(channel)

    override fun doWork(queryParams: HashMap<String, String>): Call<Void> {
        return pubnub.retrofitManager.messageActionService
            .deleteMessageAction(
                subKey = pubnub.configuration.subscribeKey,
                channel = channel,
                messageTimetoken = messageTimetoken.toString().toLowerCase(),
                actionTimetoken = actionTimetoken.toString().toLowerCase(),
                options = queryParams
            )
    }

    override fun createResponse(input: Response<Void>): PNRemoveMessageActionResult? {
        return PNRemoveMessageActionResult()
    }

    override fun operationType() = PNOperationType.PNDeleteMessageAction
}