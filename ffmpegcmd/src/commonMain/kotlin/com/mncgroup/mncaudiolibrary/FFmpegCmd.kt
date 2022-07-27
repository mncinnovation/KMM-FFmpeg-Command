package com.mncgroup.mncaudiolibrary


object FFmpegCmd {
    /**
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     */
    fun convert(inputPath: String, outputPath: String): String {
        return "-i $inputPath \"$outputPath\""
    }

    /**
     * Directly convert from one file to others
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     * @param bitrate bitrate value
     */
    fun convert(inputPath: String, outputPath: String, bitrate: Int): String {
        val ext = outputPath.split(".").last()
        return "-i $inputPath -f $ext -ab $bitrate \"$outputPath\""
    }

    /**
     * Convert any audio file to AMR
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     */
    fun convertAnyToAMR(inputPath: String, outputPath: String): String {
        return "-i $inputPath -codec amr_nb -ar 8000 -ac 1 -ab 32 \"$outputPath\""
    }

    /**
     * Convert any audio file to OGG
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     */
    fun convertAnyToOGG(inputPath: String, outputPath: String): String {
        return "-i $inputPath -acodec libvorbis \"$outputPath\""
    }

    /**
     * Convert any audio file to MP3
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     */
    fun convertAnyToMP3(inputPath: String, outputPath: String): String {
        return "-i $inputPath -acodec libmp3lame \"$outputPath\""
    }

    /**
     * Convert any audio file to AC3
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     */
    fun convertAnyToAC3(inputPath: String, outputPath: String): String {
        return "-i $inputPath -acodec ac3 \"$outputPath\""
    }

    /**
     * Convert any audio file to OPUS
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     */
    fun convertAnyToOPUS(inputPath: String, outputPath: String): String {
        return "-i $inputPath -acodec libopus \"$outputPath\""
    }

    /**
     * Convert any audio file to WAV
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     */
    fun convertAnyToWAV(inputPath : String, outputPath: String): String {
        return "-i $inputPath -acodec pcm_u8 -ar 22050 \"$outputPath\""
    }

    /**
     * Convert any audio file to AAC
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     */
    fun convertAnyToAAC(inputPath: String, outputPath: String): String {
        return "-i $inputPath -acodec aac \"$outputPath\""
    }

    /**
     * Cut or trim audio based on start and end in seconds
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     * @param start format in second -> 0
     * @param end   format in second -> 0
     */
    fun trim(inputPath: String, outputPath: String, start: Int, end: Int): String {
        return "-ss $start -i $inputPath \"$outputPath\" -t $end"
    }

    /**
     * Cut or trim audio based on start and duration in string format 00:00:00
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     * @param start     format -> "00:01:00"
     * @param duration  format -> "00:00:30"
     * @example Cut 30 seconds audio from 1:00 min. Start 1 min / end 1:30
     */
    fun trim(
        inputPath: String,
        outputPath: String,
        start: String,
        duration: String
    ): String {
        return "-ss $start -i $inputPath -o \"$outputPath\" -to $duration"
    }

    /**
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     * @param highpass highpass parameter set
     * @param lowpass lowpass parameter set
     */
    fun removeNoise(inputPath: String, outputPath: String, highpass: Int, lowpass:Int): String {
        return "-i $inputPath -af \"highpass=f=$highpass, lowpass=f=$lowpass\" \"$outputPath\""
    }

    /**
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     */
    fun removeSilence(inputPath: String, outputPath: String): String {
        return "-i $inputPath -af silenceremove=1:0:-50dB -o \"$outputPath\""
    }

    /**
     * @param inputPath source of file path audio
     * @param outputPath output path of file result
     */
    fun removeVocal(inputPath: String, outputPath: String): String {
        return "-i $inputPath -af pan=\"stereo|c0=c0|c1=-1*c1\" -ac 1 -o \"$outputPath\""
    }


    // -------- Effect tools --------
    /**
     * @param inputPath source of file path audio
     * @param outputFile output path of file result
     */
    fun robotEffect(inputPath: String, outputFile: String): String {
        return "-i $inputPath -af asetrate=11100,atempo=4/3,atempo=1/2,atempo=3/4 -o \"$outputFile\""
    }

    /**
     * @param inputPath source of file path audio
     * @param outputFile output path of file result
     */
    fun echoEffect(inputPath: String, outputFile: String, echoOption: EchoOption): String {
        return when (echoOption) {
            EchoOption.MOUNTAINS -> {
                "-i $inputPath -filter_complex \"aecho=0.8:0.9:500|1000:0.2|0.1\" -o \"$outputFile\""
            }
            else -> {
                "-i $inputPath -filter_complex \"aecho=0.8:0.9:40|50|70:0.4|0.3|0.2\" -o \"$outputFile\""
            }
        }
    }

    /**
     * @param inputPath source of file path audio
     * @param aresample is  is calculated by :
     * 44100 * total Seconds = X.
     * aresample = X / WaveForm Width.
     */
    fun getAmplitudesValues(inputPath: String, aresample: Int): String {
        return "-i \"$inputPath\" -vn -ac 1 -filter:a aresample=$aresample -map 0:a -c:a pcm_s16le -f data -"
    }

    /**
     * @param inputPath1 source of file path audio 1
     * @param inputPath2 source of file path audio 2
     * @param outputPath output path of file result
     */
    fun combineBothFiles(
        inputPath1: String,
        inputPath2: String,
        outputPath: String
    ): String {
        val ext = outputPath.split(".").last()
        return "-i $inputPath1 -i $inputPath2 -filter_complex concat=n=3:v=0:a=1 -c:a $ext -vn -o \"$outputPath\""
    }
}

enum class EchoOption {
    INDOOR,
    MOUNTAINS
}