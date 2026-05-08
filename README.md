# toml-edit-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Ftoml--edit--kotlin-blue.svg)](https://github.com/KotlinMania/toml-edit-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/toml-edit-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/toml-edit-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/toml-edit-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/toml-edit-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`toml-rs/toml`](https://github.com/toml-rs/toml).

**Original Project:** This port is based on [`toml-rs/toml`](https://github.com/toml-rs/toml). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `toml-rs/toml`

> The text below is reproduced and lightly edited from [`https://github.com/toml-rs/toml`](https://github.com/toml-rs/toml). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs so they continue to resolve from this repository.

This repo contains:
- [`toml` crate](https://github.com/toml-rs/toml/blob/HEAD/crates/toml) for serde support
- [`toml_edit` crate](https://github.com/toml-rs/toml/blob/HEAD/crates/toml_edit) for format-preserving editing of TOML
- [`toml_datetime` crate](https://github.com/toml-rs/toml/blob/HEAD/crates/toml_datetime) for a common type definition between `toml` and `toml_edit`
- [`serde_spanned` crate](https://github.com/toml-rs/toml/blob/HEAD/crates/serde_spanned) for capturing spans when deserializing keys and values
- [`toml_parser` crate](https://github.com/toml-rs/toml/blob/HEAD/crates/toml_parser): a low-level format-preserving TOML lexer and parser
- [`toml_writer` crate](https://github.com/toml-rs/toml/blob/HEAD/crates/toml_writer): a low-level interface for writing out TOML

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:toml-edit-kotlin:0.1.0")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same MIT license as the upstream [`toml-rs/toml`](https://github.com/toml-rs/toml). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the toml authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`toml-rs/toml`](https://github.com/toml-rs/toml) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
