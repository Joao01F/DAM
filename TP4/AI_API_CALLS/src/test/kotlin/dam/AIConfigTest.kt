package dam

import org.junit.jupiter.api.Assertions.*
import java.util.Properties
import kotlin.test.Test

class AIConfigTest {

    val properties = getProperties()

    @Test
    fun lowTempTest() {
        properties.apply {
            setProperty("TEMPERATURE", "0.2")
            setProperty("MAX_TOKENS", "1200")
        }

        val assistant = AIAssistantGeminiClasses(properties)

        assertEquals(0.2, assistant.temperature)
    }

    @Test
    fun highTempTest() {
        properties.apply {
            setProperty("TEMPERATURE", "0.9")
            setProperty("MAX_TOKENS", "1200")
        }

        val assistant = AIAssistantGeminiClasses(properties)

        assertEquals(0.9, assistant.temperature)
    }
}