# Story: The 400K-Document RAG System

STAR format, about 2 minutes spoken. The production-RAG version of the Moderna PDF digitization story.

## Situation

"Moderna holds more than four hundred thousand regulatory documents as PDFs — eight to twelve pages each, written for humans. My team is building the RAG system over all of them: ingest, chunk, index, retrieve, generate — under a constraint most RAG projects never face. Every answer has to trace back to the exact section of the exact document it came from. In a regulated company, that trace is the product."

## Task

"Ship a queryable knowledge base over the full corpus, with retrieval quality high enough to trust and traceability strong enough to audit."

## Action

"Three decisions mattered. First, chunking: we tried LLM-drawn semantic boundaries, and they failed — the boundaries looked sensible to a model but did not line up with how real questions land, and the trace back to source sections broke. We went back to section-based splitting driven by document structure. Second, evaluation: we had no automated measurement, so humans read chunks and judged whether retrieval produced correct answers — slow, but it caught what the metrics could not. Third, governance: two camps fought over this, no AI at all versus AI for everything. I built a risk framework from the one objective both sides share, do not harm patients, that scores each use case on what level of AI it may carry. Both sides co-author it."

## Result

"The pipeline ingests a few hundred documents a week today. The next milestone is all four hundred thousand within twelve weeks, without loading the servers that host critical manufacturing applications — so the current work is throughput engineering on the batch side. The risk framework depersonalized the conflict and both camps now build on it."

## Where this story plays

- Production RAG: chunking strategy, retrieval evaluation, traceability
- ML in regulated environments: audit-grade provenance for every answer
- Model governance: the risk framework as a leadership artifact
- Stakeholder conflict: separating people from issues via mechanism design
- Throughput engineering: scaling batch inference without touching critical systems

## Follow-ups, with the honest answers

- "Why did semantic chunking fail?" — "The LLM drew boundaries that looked sensible but did not match how questions land on content — and nobody could say why a chunk started or ended where it did, so the traceability requirement broke. Section-based chunks map exactly: this chunk is section 3.2 of that document."
- "Why no automated eval?" — "Bandwidth. We never built it, and that is the honest gap — humans reading chunks does not scale to four hundred thousand documents. Automated measurement comes before the next model change."
- "How did you get the no-AI camp on board?" — "I stopped arguing positions and built a mechanism. Starting from 'do not harm patients,' which both sides already agreed on, we score each use case instead of debating AI in the abstract."
