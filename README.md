# Bonita engine spring boot starter

This is a Proof of concept on how a spring boot starter can start an embedded engine.

The starter autoconfigure a bonita-engine, starts it and deploy all processes defined using the kotlin DSL

This should be split in multiple autoconfiguration because the spring boot starter should not depends on the kotlin DSL
