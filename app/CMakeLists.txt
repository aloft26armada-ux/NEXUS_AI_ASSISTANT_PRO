cmake_minimum_required(VERSION 3.22.1)
project("nexusai_native")

set(CMAKE_CXX_STANDARD 17)

# Internal paths definition
set(CPP_SRC_DIR ${CMAKE_CURRENT_SOURCE_DIR}/src/main/cpp)

add_library(nexus_ai SHARED
        ${CPP_SRC_DIR}/nexus_llama_bridge.cpp
        ${CPP_SRC_DIR}/nexus_whisper_bridge.cpp
)

# Target link instructions matching mobile optimization chipsets
target_link_libraries(nexus_ai
        log
        android
)
