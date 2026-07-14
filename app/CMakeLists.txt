cmake_minimum_required(VERSION 3.22.1)

project("nexus_ai")

add_library(
        nexus_llama_bridge
        SHARED
        src/main/cpp/nexus_llama_bridge.cpp)

add_library(
        nexus_whisper_bridge
        SHARED
        src/main/cpp/nexus_whisper_bridge.cpp)

find_library(
        log-lib
        log)

target_link_libraries(
        nexus_llama_bridge
        ${log-lib})

target_link_libraries(
        nexus_whisper_bridge
        ${log-lib})
