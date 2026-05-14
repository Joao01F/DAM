package dam

import okhttp3.Request
import org.json.JSONObject
import java.util.Properties
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SentimentAnalysisTest {

    private class TestAssistant : AIAssistant {
        override val properties = Properties()
        override val apiKeyName = "TEST_API_KEY"
        override var model = "test-model"

        override fun getSystem() = "TEST"

        override fun buildRequest(prompt: String): Request {
            throw UnsupportedOperationException("No real API calls are used in these tests.")
        }
    }

    private val assistant = TestAssistant()

    @Test
    fun `sentiment prompt asks for seven point JSON rating`() {
        val prompt = assistant.buildSentimentAnalysisPrompt("I love this application.")

        assertTrue(prompt.contains("7-point scale"))
        assertTrue(prompt.contains("\"rating\""))
        assertTrue(prompt.contains("\"justification\""))
        assertTrue(prompt.contains("I love this application."))
    }

    @Test
    fun `extracts Gemini response text`() {
        val json = JSONObject(
            """
            {
              "candidates": [
                {
                  "content": {
                    "parts": [
                      { "text": "{\"rating\": 6, \"justification\": \"Positive wording.\"}" }
                    ]
                  }
                }
              ]
            }
            """.trimIndent()
        )

        assertEquals(
            "{\"rating\": 6, \"justification\": \"Positive wording.\"}",
            assistant.extractResponseText(json)
        )
    }

    @Test
    fun `extracts OpenAI response text`() {
        val json = JSONObject(
            """
            {
              "choices": [
                {
                  "message": {
                    "content": "{\"rating\": 2, \"justification\": \"Negative wording.\"}"
                  }
                }
              ]
            }
            """.trimIndent()
        )

        assertEquals(
            "{\"rating\": 2, \"justification\": \"Negative wording.\"}",
            assistant.extractResponseText(json)
        )
    }
}
