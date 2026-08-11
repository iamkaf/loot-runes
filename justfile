set shell := ["bash", "-euo", "pipefail", "-c"]

default:
    @just --list

list-versions:
    @find versions -mindepth 2 -maxdepth 2 -type f -name 'gradle.properties' -printf '%h\n' | xargs -r -n1 basename | sort -V

list-loaders version:
    @grep '^project.enabled-loaders=' "versions/{{ version }}/gradle.properties" | head -n1 | cut -d= -f2- | tr ',' '\n' | sed 's/^[[:space:]]*//; s/[[:space:]]*$//' | sed '/^$/d'

list-nodes:
    @for props in versions/*/gradle.properties; do version=$(basename "$(dirname "$props")"); loaders=$(sed -nE 's/^project\.enabled-loaders=(.*)$/\1/p' "$props" | head -n1); for loader in $(printf '%s\n' "$loaders" | tr ',' '\n' | sed 's/^[[:space:]]*//; s/[[:space:]]*$//' | sed '/^$/d'); do echo "$version-$loader"; done; done | sort -V

projects:
    @./gradlew projects --console=plain

build node:
    @if ! just list-nodes | grep -Fxq "{{ node }}"; then echo "Unknown node: {{ node }}"; exit 1; fi
    @version="{{ node }}"; loader="${version##*-}"; version="${version%-*}"; ./gradlew --configure-on-demand ":$loader:$version:build" --console=plain

build-all:
    @./gradlew build --console=plain

compile-all:
    @tasks=(); for version in $(just list-versions); do tasks+=(":common:$version:compileJava"); for loader in $(just list-loaders "$version"); do tasks+=(":$loader:$version:compileJava"); done; done; ./gradlew --configure-on-demand "${tasks[@]}" --console=plain

run-client node:
    @if ! just list-nodes | grep -Fxq "{{ node }}"; then echo "Unknown node: {{ node }}"; exit 1; fi
    @version="{{ node }}"; loader="${version##*-}"; version="${version%-*}"; ./gradlew --configure-on-demand ":$loader:$version:runClient" --console=plain

datagen version="26.2":
    @if ! just list-versions | grep -Fxq "{{ version }}"; then echo "Unknown version: {{ version }}"; exit 1; fi
    @./gradlew --configure-on-demand ":fabric:{{ version }}:runDatagen" --console=plain

teakit-check node timeout="180":
    @./gradlew teakitCheck -Pteakit.node="{{ node }}" -Pteakit.timeout="{{ timeout }}"

teakit-check-all timeout="180":
    @status=0; for version in $(just list-versions); do nodes=(); pids=(); for loader in $(just list-loaders "$version"); do node="$version-$loader"; nodes+=("$node"); echo "==> $node"; ./gradlew teakitCheck -Pteakit.node="$node" -Pteakit.timeout="{{ timeout }}" > "/tmp/lootrunes-$node.teakit.log" 2>&1 & pids+=("$!"); done; for index in "${!nodes[@]}"; do node="${nodes[$index]}"; if ! wait "${pids[$index]}"; then echo "TeaKit failed: $node"; tail -n 160 "/tmp/lootrunes-$node.teakit.log"; status=1; else echo "TeaKit OK: $node"; fi; done; done; exit "$status"

horizontal-jars version="all":
    @versions="{{ version }}"; if [ "$versions" = all ]; then versions=$(just list-versions); elif ! just list-versions | grep -Fxq "$versions"; then echo "Unknown version: $versions"; exit 1; fi; for version in $versions; do echo "==> $version horizontal jar"; ./gradlew -Pmultiloader.target.versions="$version" validateHorizontalJars --console=plain || exit; done
